package ru.estartsev.edms.service;

public interface EntityCodeCreateService {
    String NAME = "edms_EntityCodeCreateService";

    String createCode(String prefix, String sequence);
}