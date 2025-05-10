package com.example.backend.controller;

import com.example.backend.model.Post;
import com.example.backend.repository.PostRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id){
	 return postRepository.findById(id)
	      .map(ResponseEntity::ok)
	      .orElse(ResponseEntity.notFound().build());
    }	 
}


