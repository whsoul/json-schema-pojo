package com.whsoul.jsch.pojo;

import java.util.List;

public class Pojo03 {

    public Integer total;
    public Integer totalPages;
    public List<Result> results = null;

    public class Result {

        public String id;
        public String createdAt;
        public Integer width;
        public Integer height;
        public String color;
        public Integer likes;
        public Boolean likedByUser;
        public String description;
        public User user;
        public List<Object> currentUserCollections = null;
        public Urls urls;
        public Links_ links;

        public class User {

            public String id;
            public String username;
            public String name;
            public String firstName;
            public String lastName;
            public String instagramUsername;
            public String twitterUsername;
            public String portfolioUrl;
            public ProfileImage profileImage;
            public Links links;

            public class ProfileImage {
                public String small;
                public String medium;
                public String large;
            }


            public class Links {

                public String self;
                public String html;
                public String photos;
                public String likes;

            }
        }

        public class Links_ {

            public String self;
            public String html;
            public String download;

        }

        public class Urls {

            public String raw;
            public String full;
            public String regular;
            public String small;
            public String thumb;

        }
    }
}