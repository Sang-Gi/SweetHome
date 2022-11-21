package com.ssafy.sweethome.favorite_region.model.service;

import com.ssafy.sweethome.favorite_region.model.FavoriteRegion;
import com.ssafy.sweethome.favorite_region.model.mapper.FavoriteRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteRegionServiceImpl implements FavoriteRegionService {
    private final FavoriteRegionMapper favoriteRegionMapper;

    @Autowired
    public FavoriteRegionServiceImpl(FavoriteRegionMapper favoriteRegionMapper) {
        this.favoriteRegionMapper = favoriteRegionMapper;
    }

    @Override
    public List<FavoriteRegion> getFavoriteRegionList(String userId) throws Exception {
        return favoriteRegionMapper.selectFavoriteRegionList(userId);
    }

    @Override
    public boolean deleteFavoriteRegion(FavoriteRegion favoriteRegion) throws Exception {
        return favoriteRegionMapper.deleteFavoriteRegion(favoriteRegion) == 1;
    }

    @Override
    public boolean insertFavoriteRegion(FavoriteRegion favoriteRegion) throws Exception {
        return favoriteRegionMapper.insertFavoriteRegion(favoriteRegion) == 1;
    }
}
