package com.ssafy.sweethome.favorite_region.model.mapper;

import com.ssafy.sweethome.favorite_region.model.FavoriteRegion;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface FavoriteRegionMapper {
    List<FavoriteRegion> selectFavoriteRegionCodeList(String userId) throws SQLException;

    int deleteFavoriteRegion(FavoriteRegion favoriteRegion) throws SQLException;

    int insertFavoriteRegion(FavoriteRegion favoriteRegion) throws SQLException;

    List<String> selectAddressByDongCode(String dongCode) throws SQLException;
}
