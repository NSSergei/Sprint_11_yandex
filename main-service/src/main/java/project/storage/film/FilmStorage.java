package project.storage.film;

import lombok.Getter;
import project.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface FilmStorage {
    Film addFilm(Film film);
    void deleteFilm(long id);
    Film updateFilm(Film film);
    Collection<Film> getFilms();
}
