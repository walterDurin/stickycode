package net.stickycode.resource;



public interface ResourceCodecRegistry {

  ResourceCodec<?> find(Class<?> class1);

}
