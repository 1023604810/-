package cn.fvti.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fvti.springboot.entity.Article;
import cn.fvti.springboot.repository.ArticleRepository;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    
    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
    
    @GetMapping("/recommended")
    public List<Article> getRecommendedArticles() {
        return articleRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }
    
    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody Article article) {
        Article existingArticle = articleRepository.findById(id).orElse(null);
        if (existingArticle != null) {
            existingArticle.setTitle(article.getTitle());
            existingArticle.setIntroduce(article.getIntroduce());
            existingArticle.setContent(article.getContent());
            existingArticle.setAuthor(article.getAuthor());
            existingArticle.setReadNum(article.getReadNum());
            existingArticle.setTime(article.getTime());
            return articleRepository.save(existingArticle);
        }
        return null;
    }
}