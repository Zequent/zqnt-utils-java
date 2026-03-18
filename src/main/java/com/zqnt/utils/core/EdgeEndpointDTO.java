package com.zqnt.utils.core;

import com.zqnt.utils.common.proto.AssetTypeEnum;
import com.zqnt.utils.common.proto.AssetVendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdgeEndpointDTO {

	private String endpoint;

	private Boolean online;

	private AssetTypeEnum assetType;

	private AssetVendor assetVendor;


}
