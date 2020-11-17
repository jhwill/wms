package com.cloudo.wms.modules.core.api.warehouse;

import com.cloudo.wms.exceptions.ResourceIntegrityException;
import com.cloudo.wms.lib._BaseApi;
import com.cloudo.wms.modules.core.models.warehouse.Shelf;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.warehouse.ShelfService;
import com.cloudo.wms.modules.core.services.warehouse.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Api(value = "Shelf API")
@RestController
@RequestMapping({"/api/core/Shelf", "/api/v1/core/Shelf"})
public class ShelfController extends _BaseApi<Shelf> {

    @Autowired
    private ShelfService shelfService;

//    @ApiOperation(value = "Assigns menus to role")
//    @RequestMapping(value = "/{id}/assignMenus", method = RequestMethod.PUT)
//    public void assignMenus(@PathVariable(name = "id") long id, @RequestBody LongsWrapper menuIds) {
//        this.roleService.assignMenus(id, menuIds.getValues());
//    }

    @Override
    public Page<Shelf> get(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return super.get(pageable);
    }
    @ApiOperation(value = "Search by keywords")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<Shelf> search(
            @RequestParam(name = "keywords", required = false) String keywords,
            @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return this.shelfService.searchShelf(keywords, pageable);
    }

    @Override
    public void delete(@PathVariable(name = "id") long id) throws ResourceIntegrityException {
        try {
            super.delete(id);
        } catch (Exception ex) {
            throw new ResourceIntegrityException("admin.modules.core.shelf.errForDelete", ex);
        }

    }
}
