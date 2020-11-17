package com.cloudo.wms.modules.core.api.warehouse;

import com.cloudo.wms.exceptions.ResourceIntegrityException;
import com.cloudo.wms.lib._BaseApi;
import com.cloudo.wms.modules.core.models.warehouse.Box;
import com.cloudo.wms.modules.core.models.warehouse.Warehouse;
import com.cloudo.wms.modules.core.services.warehouse.BoxService;
import com.cloudo.wms.modules.core.services.warehouse.WarehouseService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Api(value = "box API")
@RestController
@RequestMapping({"/api/core/box", "/api/v1/core/box"})
public class BoxController extends _BaseApi<Box> {

    @Autowired
    private BoxService boxService;

//    @ApiOperation(value = "Assigns menus to role")
//    @RequestMapping(value = "/{id}/assignMenus", method = RequestMethod.PUT)
//    public void assignMenus(@PathVariable(name = "id") long id, @RequestBody LongsWrapper menuIds) {
//        this.roleService.assignMenus(id, menuIds.getValues());
//    }

    @Override
    public Page<Box> get(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return super.get(pageable);
    }
    @ApiOperation(value = "Search by keywords")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<Box> search(
            @RequestParam(name = "keywords", required = false) String keywords,
            @PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return this.boxService.searchBox(keywords, pageable);
    }

    @ApiOperation(value = "Save Box")
    @RequestMapping(value = "/saveBox", method = RequestMethod.POST)
    public Page<Box> saveBox(@RequestBody String param) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(param).getAsJsonObject();
        String boxReference = jsonObject.get("boxReference").getAsString();
        String shelfReference = jsonObject.get("shelfReference").getAsString();

        return this.boxService.saveBox(boxReference, shelfReference);
    }

    @Override
    public void delete(@PathVariable(name = "id") long id) throws ResourceIntegrityException {
        try {
            super.delete(id);
        } catch (Exception ex) {
            throw new ResourceIntegrityException("admin.modules.core.box.errForDelete", ex);
        }

    }
}
