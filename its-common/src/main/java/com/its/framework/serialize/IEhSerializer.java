package com.its.framework.serialize;

import com.its.framework.serialize.reader.Reader;
import com.its.framework.serialize.writer.Writer;

public abstract interface IEhSerializer<T>
{
  public abstract void serialize(Writer paramWriter, T paramT);

  public abstract T deSerialize(Reader paramReader);
}
