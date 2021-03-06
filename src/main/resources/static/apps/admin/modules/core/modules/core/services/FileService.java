/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.modules.core.services;

import com.cloudo.wms.lib._BaseService;
import com.cloudo.wms.modules.core.services.repositories.FileRepository;
import com.cloudo.wms.modules.core.models.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FileService extends _BaseService<File> {

    @Autowired
    private FileRepository fileRepository;

    public File getByUuid(String uuid) {
        return this.fileRepository.findByUuid(uuid);
    }

    public void setRef(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            this.fileRepository.setRef(ids);
        }
    }

    public List<File> getFilesByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return new ArrayList<>();
        }
        return this.fileRepository.findByIds(ids);
    }
}
