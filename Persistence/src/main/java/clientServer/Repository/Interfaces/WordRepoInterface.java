package clientServer.Repository.Interfaces;

import Domain.Word;

import java.util.List;

public interface WordRepoInterface extends IRepository<Integer, Word>{
    List<Word> getALlWord();
    List<Word> generateRandomWord(Integer count);
}
