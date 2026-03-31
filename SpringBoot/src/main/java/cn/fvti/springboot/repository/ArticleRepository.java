package cn.fvti.springboot.repository;

import cn.fvti.springboot.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}