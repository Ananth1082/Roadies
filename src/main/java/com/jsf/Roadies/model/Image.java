    package com.jsf.Roadies.model;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.sql.Blob;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    public class Image {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String fileName;
        private String fileType;

        @Lob
        private Blob image;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;
    }