package com.google.inject.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.InternalServletModule.BackwardsCompatibleServletContextProvider;


public class ExplicitBindingsFixitModule
    extends AbstractModule {

  @Override
  protected void configure() {
    // if you turn on explicit bindings then this puppy is not explicitly bound
    // oops, and its only in svn cause Google don't bother to release stuff...
    // so here is a dodgy fix
    bind(BackwardsCompatibleServletContextProvider.class);
  }

}
