package guru.hakandurmaz.blog.controller;

import guru.hakandurmaz.blog.payload.post.CreatePostRequest;
import guru.hakandurmaz.blog.payload.post.GetPostDto;
import guru.hakandurmaz.blog.payload.post.UpdatePostRequest;
import guru.hakandurmaz.blog.service.PostService;
import guru.hakandurmaz.blog.utils.constants.AppConstants;
import guru.hakandurmaz.blog.utils.results.DataResult;
import guru.hakandurmaz.blog.utils.results.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/posts")
public class PostController {

  private PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public Result createPost(@Valid @RequestBody CreatePostRequest postRequest) {
    return this.postService.createPost(postRequest);
  }

  @GetMapping
  public DataResult<GetPostDto> getAllPosts(
      @RequestParam(
              value = "pageNo",
              defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
              required = false)
          int pageNo,
      @RequestParam(
              value = "pageSize",
              defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
              required = false)
          int pageSize,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
          String sortBy,
      @RequestParam(
              value = "sortDir",
              defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
              required = false)
          String sortDir) {
    log.info(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir).toString());
    return this.postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
  }

  @GetMapping(value = "{id}")
  public DataResult<GetPostDto> getPostById(@PathVariable(name = "id") long id) {
    return postService.getPostById(id);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("{id}")
  public Result updatePost(@Valid @RequestBody UpdatePostRequest postRequest) {
    return postService.updatePost(postRequest);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("{id}")
  public Result deletePost(@PathVariable(name = "id") long id) {
    return postService.deletePostById(id);
  }

  @GetMapping({"/search"})
  public DataResult searchPost(@RequestParam("query") String query) {
    return postService.listOfPosts(query);
  }
}
