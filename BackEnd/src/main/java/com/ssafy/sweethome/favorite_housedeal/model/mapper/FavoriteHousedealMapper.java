package com.ssafy.sweethome.favorite_housedeal.model.mapper;

import com.ssafy.sweethome.favorite_housedeal.model.FavoriteHousedeal;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface FavoriteHousedealMapper {
    List<FavoriteHousedeal> selectFavoriteHousedealList(String userId) throws SQLException;

    int deleteFavoriteHousedeal(FavoriteHousedeal favoriteHousedeal) throws SQLException;

    int insertFavoriteHousedeal(FavoriteHousedeal favoriteHousedeal) throws SQLException;
}
