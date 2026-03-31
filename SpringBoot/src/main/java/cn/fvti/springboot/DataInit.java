package cn.fvti.springboot;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.fvti.springboot.entity.Article;
import cn.fvti.springboot.entity.Message;
import cn.fvti.springboot.entity.User;
import cn.fvti.springboot.repository.ArticleRepository;
import cn.fvti.springboot.repository.MessageRepository;
import cn.fvti.springboot.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataInit implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ArticleRepository articleRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 检查数据库是否已初始化
        System.out.println("开始初始化数据...");
        System.out.println("当前用户数量: " + userRepository.count());
        System.out.println("当前留言数量: " + messageRepository.count());
        // 强制重新初始化用户数据
        initUsers();
        if (messageRepository.count() == 0) {
            initMessages();
        }
        // 暂时跳过文章数据的初始化，先让应用程序启动
        // if (articleRepository.count() == 0) {
        //     initArticles();
        // }
        // 手动创建一些简短的文章数据
        if (articleRepository.count() == 0) {
            createShortArticles();
        }
        System.out.println("数据初始化完成！");
        System.out.println("初始化后用户数量: " + userRepository.count());
        System.out.println("初始化后留言数量: " + messageRepository.count());
    }
    
    private void initUsers() {
        // 创建或更新默认用户
        User admin = userRepository.findByUsername("admin");
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            System.out.println("创建新的admin用户");
        } else {
            System.out.println("更新现有的admin用户");
        }
        admin.setPassword("admin");
        userRepository.save(admin);
        
        User user1 = userRepository.findByUsername("user1");
        if (user1 == null) {
            user1 = new User();
            user1.setUsername("user1");
            System.out.println("创建新的user1用户");
        } else {
            System.out.println("更新现有的user1用户");
        }
        user1.setPassword("user1");
        userRepository.save(user1);
        System.out.println("用户数据初始化完成");
    }
    
    private void initMessages() {
        try {
            // 读取data.json文件
            InputStream inputStream = getClass().getResourceAsStream("/data.json");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(inputStream);
            
            // 解析messageList
            JsonNode messageListNode = rootNode.get("messageList");
            if (messageListNode.isArray()) {
                for (JsonNode messageNode : messageListNode) {
                    Message message = new Message();
                    message.setId(messageNode.get("id").asLong());
                    message.setEmail(messageNode.get("email").asText());
                    message.setName(messageNode.get("name").asText());
                    message.setContent(messageNode.get("content").asText());
                    message.setGender(messageNode.get("gender").asInt());
                    message.setSupport(messageNode.get("support").asInt());
                    message.setHasSup(messageNode.get("has_sup").asBoolean());
                    message.setUnnamed(messageNode.get("unnamed").asBoolean());
                    message.setTime(messageNode.get("time").asLong());
                    messageRepository.save(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void initArticles() {
        try {
            // 读取data.json文件
            InputStream inputStream = getClass().getResourceAsStream("/data.json");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(inputStream);
            
            // 解析articleList
            JsonNode articleListNode = rootNode.get("articleList");
            JsonNode articleContentNode = rootNode.get("article");
            
            if (articleListNode.isArray() && articleContentNode.isArray()) {
                for (JsonNode articleNode : articleListNode) {
                    Article article = new Article();
                    article.setId(articleNode.get("id").asLong());
                    article.setTitle(articleNode.get("title").asText());
                    article.setIntroduce(articleNode.get("introduce").asText());
                    article.setAuthor(articleNode.get("author").asText());
                    article.setReadNum(articleNode.get("readNum").asInt());
                    article.setTime(articleNode.get("time").asLong());
                    
                    // 查找对应的内容
                    for (JsonNode contentNode : articleContentNode) {
                        if (contentNode.get("id").asLong() == article.getId()) {
                            String content = contentNode.get("content").asText();
                            // 截断内容，确保长度不超过数据库限制
                            if (content.length() > 1000) {
                                content = content.substring(0, 1000);
                            }
                            article.setContent(content);
                            break;
                        }
                    }
                    
                    articleRepository.save(article);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void createShortArticles() {
        System.out.println("开始创建简短的文章数据...");
        
        // 创建5篇简短的文章
        Article article1 = new Article();
        article1.setTitle("三坊七巷名人介绍");
        article1.setIntroduce("跟三坊七巷相关的都有哪些历史名人？");
        article1.setContent("三坊七巷是福州的历史文化街区，走出了许多历史名人，如林则徐、沈葆桢等。");
        article1.setAuthor("张明");
        article1.setReadNum(170);
        article1.setTime(System.currentTimeMillis());
        articleRepository.save(article1);
        
        Article article2 = new Article();
        article2.setTitle("福州特产介绍");
        article2.setIntroduce("福州特产有哪些最出名？");
        article2.setContent("福州特产有寿山石雕、福州软木画、脱胎漆器等工艺品，还有福州橄榄、福桔等水果。");
        article2.setAuthor("陈晓");
        article2.setReadNum(112);
        article2.setTime(System.currentTimeMillis());
        articleRepository.save(article2);
        
        Article article3 = new Article();
        article3.setTitle("福建旅游必去十大景点");
        article3.setIntroduce("福建旅游景点10大排名");
        article3.setContent("福建旅游必去的十大景点包括厦门鼓浪屿、武夷山、太姥山、永定土楼等。");
        article3.setAuthor("李涛");
        article3.setReadNum(210);
        article3.setTime(System.currentTimeMillis());
        articleRepository.save(article3);
        
        Article article4 = new Article();
        article4.setTitle("福建十大名茶");
        article4.setIntroduce("福建有哪些著名的茶叶？");
        article4.setContent("福建十大名茶包括武夷山大红袍、安溪铁观音、正山小种、武夷岩茶等。");
        article4.setAuthor("陈星");
        article4.setReadNum(314);
        article4.setTime(System.currentTimeMillis());
        articleRepository.save(article4);
        
        Article article5 = new Article();
        article5.setTitle("鼓浪屿介绍");
        article5.setIntroduce("厦门市国家5A级旅游景区");
        article5.setContent("鼓浪屿是厦门的一个小岛，以其独特的建筑风格和音乐文化而闻名，是国家5A级旅游景区。");
        article5.setAuthor("吴维");
        article5.setReadNum(405);
        article5.setTime(System.currentTimeMillis());
        articleRepository.save(article5);
        
        System.out.println("简短的文章数据创建完成！");
    }
}