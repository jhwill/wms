/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.modules.cms.services;

import javax.transaction.Transactional;

import net.bndy.ftsi.NoKeyDefinedException;
import net.bndy.lib.CollectionHelper;
import net.bndy.lib.IOHelper;
import net.bndy.lib.StringHelper;
import com.cloudo.wms.ApplicationContext;
import com.cloudo.wms.modules.cms.IndexModel;
import com.cloudo.wms.modules.cms.models.BoType;
import com.cloudo.wms.modules.cms.models.Page;
import com.cloudo.wms.modules.cms.services.repositories.PageRepository;
import com.cloudo.wms.modules.core.models.File;
import com.cloudo.wms.modules.core.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudo.wms.modules.cms.models.*;
import com.cloudo.wms.modules.cms.services.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PageService extends _BaseService<Page> {

    @Autowired
    private FileService fileService;
    @Autowired
    private PageRepository pageRepository;

    public Page getByChannelId(long channelId) {
        Page result = this.pageRepository.findByChannelId(channelId);
        return result;
    }

    public Page getByTitle(String title) {
        return this.pageRepository.findByTitle(title);
    }

    public int countByChannelId(long channelId) {
        return this.pageRepository.countByChannelId(channelId);
    }

    public void deleteByChannelId(long channelId) {
        Page page = this.pageRepository.findByChannelId(channelId);
        if (page != null) {
            this.delete(page.getId());
        }
    }

    @Override
    public boolean delete(long id) {
        Page p = this.pageRepository.findOne(id);
        if (p != null) {
            this.deleteComments(p.getId());
            this.deleteAttachments(p.getId());
        }

        try {
            ApplicationContext.getIndexService().deleteIndex(IndexModel.class, id);
        } catch (NoKeyDefinedException ex) {
            ex.printStackTrace();
        }

        return super.delete(id);
    }

    @Override
    public Page save(Page entity) {
        if (entity.getId() != null) {
            Page origin = this.get(entity.getId());
            List<File> filesToDelete = new ArrayList<>();
            if (origin != null && origin.getAttachments() != null) {
                for (File f: origin.getAttachments()) {
                    if (entity.getAttachments() == null || !CollectionHelper.contains(entity.getAttachments(), (item) -> item.getId() == f.getId())) {
                       filesToDelete.add(f);
                    }
                }
            }

            for (File f: filesToDelete) {
                IOHelper.forceDelete(ApplicationContext.getFileFullPath(f.getPath()));
                this.fileService.delete(f.getId());
            }
        }

        if (!CollectionHelper.isNullOrEmpty(entity.getAttachments())) {
            this.fileService.setRef(CollectionHelper.convert(entity.getAttachments(), (x -> x.getId())));
        }

        entity = super.save(entity);

        try {
            ApplicationContext.getIndexService().updateIndex(new IndexModel(
                entity.getId(),
                entity.getTitle(),
                StringHelper.title2Url(entity.getTitle()),
                StringHelper.stripHtml(entity.getContent()),
                BoType.Page.getName()
            ));
        } catch (NoKeyDefinedException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return entity;
    }

    public void transfer(long sourceChannelId, long targetChannelId) {
        Page sourcePage = this.getByChannelId(sourceChannelId);
        Page targetPage = this.getByChannelId(targetChannelId);
        if (sourcePage != null) {
            if (targetPage == null) {
                targetPage = new Page();
                targetPage.setChannelId(targetChannelId);
                targetPage.setContent(sourcePage.getContent());
            } else {
                targetPage.setContent(targetPage.getContent() + sourcePage.getContent());
            }
            targetPage = this.pageRepository.saveAndFlush(targetPage);

            this.transferAttachment(sourcePage.getId(), targetPage.getId());
            this.transferComments(sourcePage.getId(), targetPage.getId());

            this.pageRepository.transferChannel(sourcePage.getId(), targetPage.getId());
            this.pageRepository.delete(sourcePage.getId());
        }
    }
}
