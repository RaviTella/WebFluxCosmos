package com.webFlux.cosmos.resilience.cosmos;

import com.webFlux.cosmos.resilience.model.Book;
import com.webFlux.cosmos.resilience.model.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BookRepository bookRepository;

    @Autowired
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @PostConstruct
    public void loadBookss() {
        List<Book> books = new ArrayList<Book>();
        books.add(new Book("1", "Databases", "99847", "Seven Databases in Seven Weeks", "Luc Perkins", "A Guide to Modern Databases and the NoSQL Movement", new BigDecimal(47.00), new BigDecimal(43.00), "https://mtchouimages.blob.core.windows.net/books/SevenDatabasesInSevenWeeks.jpg"));
        books.add(new Book("2", "Programming Languages", "88297", "The Rust Programming Language", "Steve Klabnik", "The official book on the Rust programming language", new BigDecimal(55.00), new BigDecimal(49.00), "https://mtchouimages.blob.core.windows.net/books/RustProgrammingLanguage.jpg"));
        books.add(new Book("3", "Programming Languages", "68597", "Unit Testing", "Vladimir Khorikov", "Principles, Practices, and Patterns 1st Edition", new BigDecimal(55.00), new BigDecimal(49.00), "https://mtchouimages.blob.core.windows.net/books/unitTesting.jpg"));
        books.add(new Book("4", "Programming Languages", "01264", "Learning Concurrent Programming in Scala", "Aleksandar Prokopec", "Learn the art of building concurrent applications", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/Scala.jpg"));
        books.add(new Book("5", "Databases", "44897", "The Practitioner's Guide to Graph Data", "Denise Gosnell", "Applying Graph Thinking and Graph Technologies to Solve Complex Problems 1st Edition", new BigDecimal(45.00), new BigDecimal(35.00), "https://mtchouimages.blob.core.windows.net/books/GuideToGraphDatabases.jpg"));
        books.add(new Book("6", "Databases", "11201", "Microsoft Azure SQL", "Leonard G.Lobel", "Setp by step guide for developers", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/AzureSQL.jpg"));
        books.add(new Book("7", "Programming Languages", "28526", "The Pragmatic Programmer", "Andrew Hunt", "Your Journey To Mastery", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/pragmaticProgrammer.jpg"));
        books.add(new Book("8", "Programming Languages", "95298", "Programming Microsoft Azure Service fabric", "Haishi Bai", "Service fabric for developers", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/ServiceFabric.jpg"));
        books.add(new Book("9", "Software Architecture", "95233", "Become An Awesome Software Architect", "Anatoly Volkhover", "Software architecture for developers", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/softwareArchitect.jpg"));
        books.add(new Book("10", "Software Design", "65433", "Designing Data-Intensive Applications", "Martin Kleppmann", "The Big Ideas Behind Reliable, Scalable, and Maintainable Systems", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/dataIntensiveApplication.jpg"));
        books.add(new Book("11", "Programming Languages", "75493", "Effective Java", "Joshua Bloch", "Best practices for the java platform", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/effectivejava.jpg"));
        books.add(new Book("12", "Programming Languages", "75493", "Clean Code", "Robert C. Martin", "A Handbook of Agile Software Craftsmanship", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/cleanCode.jpg"));
        books.add(new Book("13", "Programming Languages", "76993", "Black Hat Go", "Tom Steele", "Go Programming for Hackers and Presenters", new BigDecimal(60.00), new BigDecimal(50.00), "https://mtchouimages.blob.core.windows.net/books/blackHatGo.jpg"));
        books.add(new Book("14", "Programming Languages", "76093", "C# 7.0 in a Nutshell", "Joseph Albahari", "The Definitive Reference", new BigDecimal(70.00), new BigDecimal(60.00), "https://mtchouimages.blob.core.windows.net/books/csharpInANutshell.jpg"));
        books.add(new Book("15", "Programming Languages", "16093", "Get Programming With Go", "Nathan Youngman", "Learn GO Programming Fast", new BigDecimal(66.00), new BigDecimal(50.00), "https://mtchouimages.blob.core.windows.net/books/getProgrammingWithGo.jpg"));
        books.add(new Book("16", "Programming Languages", "13093", "Go In Practice", "Matt Butcher", "Includes 70 Techniques", new BigDecimal(52.00), new BigDecimal(40.00), "https://mtchouimages.blob.core.windows.net/books/GoInPractice.jpg"));
        books.add(new Book("17", "Databases", "63692", "High Performance MySQL", "Baron Schwartz", "Optimization, Backups, and Replication", new BigDecimal(67.00), new BigDecimal(52.00), "https://mtchouimages.blob.core.windows.net/books/highPerformaceMysql.jpg"));
        books.add(new Book("18", "Programming Languages", "27692", "Mastering Kotlin", "Nate Ebel", "Learn advanced Kotlin programming techniques to build apps for Android, iOS, and the web", new BigDecimal(77.00), new BigDecimal(59.00), "https://mtchouimages.blob.core.windows.net/books/masterring-kotlin.jpg"));
        books.add(new Book("19", "Programming Languages", "30697", "Node Cookbook", "David Mark Clements", "Actionable solutions for the full spectrum of Node.js 8 development", new BigDecimal(40.00), new BigDecimal(38.00), "https://mtchouimages.blob.core.windows.net/books/nodeCookBook.jpg"));
        books.add(new Book("20", "Software Design", "76297", "Node.js Design Patterns", "Mario Casciaro", "Master best practices to build modular and scalable server-side web applications", new BigDecimal(60.00), new BigDecimal(58.00), "https://mtchouimages.blob.core.windows.net/books/node-design-patterns.jpg"));
        books.add(new Book("21", "Software Architecture", "96297", "Patterns of Enterprise Application Architecture", "Martin Fowler", "Designing, Building, and Deploying Enterprice Solutions", new BigDecimal(65.00), new BigDecimal(58.00), "https://mtchouimages.blob.core.windows.net/books/patternsOfEAA.jpg"));
        books.add(new Book("22", "Programming Languages", "96297", "Programming C# 8.0", "Ian Griffiths", "Build Cloud, Web, and Desktop Applications", new BigDecimal(75.00), new BigDecimal(59.00), "https://mtchouimages.blob.core.windows.net/books/programmingcsharp8.0.jpg"));
        books.add(new Book("23", "Computer Networking", "95201", "Learning Docker Networking", "Rajdeep Das", "Docker networking deep dive", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/DockerNetworking.jpg"));
        books.add(new Book("24", "Programming Languages", "95298", "Spring Microservices", "Rajesh RV", "Build scalable microservices with Spring and Docker", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/SpringMicroServices.jpg"));
        books.add(new Book("25", "Databases", "67597", "Cassandra, The Definitive Guide", "Jeff Carpenter", "Distributed Data at Web Scale 3rd Edition", new BigDecimal(65.00), new BigDecimal(59.00), "https://mtchouimages.blob.core.windows.net/books/CassandraDefinitiveguide.jpg"));
        books.add(new Book("26", "Programming Languages", "67587", "Clean Code", "Robert C. Martin", "A Handbook of Agile Software Craftsmanship 1st Edition", new BigDecimal(49.00), new BigDecimal(35.00), "https://mtchouimages.blob.core.windows.net/books/cleanCode.jpg"));
        books.add(new Book("27", "containers", "23587", "Docker in Practice", "Ian Miell", "2nd Edition", new BigDecimal(49.00), new BigDecimal(44.00), "https://mtchouimages.blob.core.windows.net/books/DockerInPractice.jpg"));
        books.add(new Book("28", "Databases", "23227", "Elasticsearch in Action", "Ian Miell", "1st Edition", new BigDecimal(44.00), new BigDecimal(42.00), "https://mtchouimages.blob.core.windows.net/books/ElasticSearchInAction.jpg"));
        books.add(new Book("29", "Programming Algorithms", "24697", "Graph Algorithms", "Mark Needham", "Practical Examples in Apache Spark and Neo4j 1st Edition", new BigDecimal(45.00), new BigDecimal(41.00), "https://mtchouimages.blob.core.windows.net/books/GraphAlgorithms.jpg"));
        books.add(new Book("30", "Security", "23123", "Modern Authentication with AzureAD ", "Vittorio Bertocci", "Azure active directory capabilities", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/AzureAD.jpg"));
        books.add(new Book("31", "Programming Algorithms", "43147", "Java Concurrency in Practice", "Brian Goetz", "1st Edition", new BigDecimal(59.00), new BigDecimal(19.00), "https://mtchouimages.blob.core.windows.net/books/JavaConcurrency.jpg"));
        books.add(new Book("32", "Messaging Systems", "53147", "Kafka, The Definitive Guide", "Neha Narkhede", "Real-Time Data and Stream Processing at Scale", new BigDecimal(54.00), new BigDecimal(35.00), "https://mtchouimages.blob.core.windows.net/books/kaafkDefnitiveGuide.jpg"));
        books.add(new Book("33", "Databases", "58147", "MongoDB, The Definitive Guide", "Shannon Bradshaw", "Powerful and Scalable Data Storage 3rd Edition", new BigDecimal(35.00), new BigDecimal(28.00), "https://mtchouimages.blob.core.windows.net/books/mongodb.jpg"));
        books.add(new Book("34", "Programming Algorithms", "77147", "Programming Ruby 1.9 & 2.0", "Dave Thomas", "The Pragmatic Programmers' Guide (The Facets of Ruby) 4th Edition", new BigDecimal(30.00), new BigDecimal(18.00), "https://mtchouimages.blob.core.windows.net/books/ProgrammingInRuby.jpg"));
        books.add(new Book("35", "Messaging Systems", "79847", "RabbitMQ Cookbook", "Sigismondo Boschi", "Over 70 practical recipes to help you build messaging applications", new BigDecimal(44.00), new BigDecimal(39.00), "https://mtchouimages.blob.core.windows.net/books/rabbitMQCookBook.jpg"));
        books.add(new Book("36", "Containers", "01234", "Getting Started with kubernetes", "Jonathan Baier", "Learn Kubernetes the right way", new BigDecimal(40.00), new BigDecimal(30.00), "https://mtchouimages.blob.core.windows.net/books/Kubernetes.jpg"));
        Flux
                .fromIterable(books)
                .flatMap(this.bookRepository::upsertBook)
                .blockLast();
    }

}
