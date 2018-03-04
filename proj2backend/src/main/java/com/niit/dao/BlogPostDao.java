package com.niit.dao;

import java.util.List;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.User;

public interface BlogPostDao {
void saveBlogPost(BlogPost blogPost);
List<BlogPost> getBlogs(int approved);
BlogPost getBlogById(int id);
void updateBlogPost(BlogPost blogPost,String rejectionReason);
void addComment(BlogComment blogComment);
}