package com.ssafy.sweethome.favorite_region.model.service;

import com.ssafy.sweethome.favorite_region.model.FavoriteRegion;

import java.util.List;

public interface FavoriteRegionService {
    List<FavoriteRegion> getFavoriteRegionList(String userId) throws Exception;

    boolean deleteFavoriteRegion(FavoriteRegion favoriteRegion) throws Exception;

    boolean insertFavoriteRegion(FavoriteRegion favoriteRegion) throws Exception;
}
