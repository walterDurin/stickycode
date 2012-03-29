package net.stickycode.rant.model;

import java.util.List;

import org.joda.time.DateTime;

public class Rant {

  private DateTime created;

  private String summary;

  private String description;

  private List<Rant> children;
}
