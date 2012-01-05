package net.stickycode.configured.content;

public interface LocalisedElementRepository
    extends Iterable<LocalisedElement> {

  void register(LocalisedElement content)
    throws DuplicateContentConfigurationException;

}
