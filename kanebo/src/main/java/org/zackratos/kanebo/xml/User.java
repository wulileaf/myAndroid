package org.zackratos.kanebo.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class User {

    @Element
    public String success;

    @Element
    public String username;

    @Element
    public String postname;
}
