package project.storage.film;

import project.model.Film;

import java.util.Collection;


public interface FilmStorage {

    Film addFilm(Film film);

    void deleteFilm(long id);

    Film updateFilm(Film film);

    Collection<Film> getFilms();
}
