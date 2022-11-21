package com.ssafy.sweethome.favorite_housedeal.model.service;

import com.ssafy.sweethome.favorite_housedeal.model.FavoriteHousedeal;
import com.ssafy.sweethome.favorite_housedeal.model.mapper.FavoriteHousedealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteHousedealServiceImpl implements FavoriteHousedealService {
    private final FavoriteHousedealMapper favoriteHousedealMapper;

    @Autowired
    public FavoriteHousedealServiceImpl(FavoriteHousedealMapper favoriteHousedealMapper) {
        this.favoriteHousedealMapper = favoriteHousedealMapper;
    }

    @Override
    public List<FavoriteHousedeal> getFavoriteHousedealList(String userId) throws Exception {
        return favoriteHousedealMapper.selectFavoriteHousedealList(userId);
    }

    @Override
    public boolean deleteFavoriteHousedeal(FavoriteHousedeal favoriteHousedeal) throws Exception {
        return favoriteHousedealMapper.deleteFavoriteHousedeal(favoriteHousedeal) == 1;
    }

    @Override
    public boolean insertFavoriteHousedeal(FavoriteHousedeal favoriteHousedeal) throws Exception {
        return favoriteHousedealMapper.insertFavoriteHousedeal(favoriteHousedeal) == 1;
    }
}
