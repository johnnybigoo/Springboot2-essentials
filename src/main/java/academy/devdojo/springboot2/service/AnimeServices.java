package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeServices {

    private final AnimeRepository animeRepository;
    public List<Anime> listAll(){

        return animeRepository.findAll();
    }

    public Anime findByIdOrThrownBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found. "));

    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrownBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrownBadRequestException(animePutRequestBody.getId());
        Anime anime = Anime.builder()
                .id(savedAnime.getId())
                .name(animePutRequestBody.getName())
                .build();

        animeRepository.save(anime);
    }
}
