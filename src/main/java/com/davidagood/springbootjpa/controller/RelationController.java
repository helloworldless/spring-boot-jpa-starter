package com.davidagood.springbootjpa.controller;

import com.davidagood.springbootjpa.repository.RelationRepository;
import com.davidagood.springbootjpa.entity.Relation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
class RelationController {

    private final RelationRepository relationRepository;

    @GetMapping("/relation")
    public List<Relation> getAllRelations() {
        return relationRepository.findAll();
    }

    @PostMapping("/relation")
    public Relation createRelation(@RequestParam("parent") int parent, @RequestParam("child") int child) {
        var relation = new Relation(parent, child);
        return relationRepository.save(relation);
    }

    @DeleteMapping("/relation")
    public void deleteAllRelations() {
        relationRepository.deleteAll();
    }

    @PostMapping("/seed/{count}")
    public List<Relation> seedRelations(@PathVariable(value = "count") int count) {
        List<Relation> relations = IntStream.rangeClosed(1, count)
            .boxed()
            .map(i -> new Relation(i, i + 1))
            .collect(toList());
        return relationRepository.saveAll(relations);
    }

}
