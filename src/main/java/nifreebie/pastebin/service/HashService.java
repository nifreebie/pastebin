package nifreebie.pastebin.service;

public interface HashService {

  String getUniqueHash();

  void generate();

  boolean isExistByHash(String hash);

  boolean hasPrepared(int minCount);
}