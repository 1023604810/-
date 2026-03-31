package cn.fvti.springboot.repository;

import cn.fvti.springboot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
