package com.cloudo.wms.modules.core.api;

import io.swagger.annotations.Api;
import com.cloudo.wms.lib._BaseApi;
import com.cloudo.wms.modules.core.models.File;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "File Resource API")
@RestController
@RequestMapping({"/api/core/files"})
public class FileController  extends _BaseApi<File> {

}
