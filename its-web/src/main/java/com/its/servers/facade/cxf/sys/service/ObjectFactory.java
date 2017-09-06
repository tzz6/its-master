
package com.its.servers.facade.cxf.sys.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.its.servers.facade.cxf.sys.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetSysUserCount_QNAME = new QName("http://service.sys.cxf.facade.servers.its.com/", "getSysUserCount");
    private final static QName _GetSysUserCountResponse_QNAME = new QName("http://service.sys.cxf.facade.servers.its.com/", "getSysUserCountResponse");
    private final static QName _GetSysUserList_QNAME = new QName("http://service.sys.cxf.facade.servers.its.com/", "getSysUserList");
    private final static QName _GetSysUserListResponse_QNAME = new QName("http://service.sys.cxf.facade.servers.its.com/", "getSysUserListResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.its.servers.facade.cxf.sys.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSysUserCount }
     * 
     */
    public GetSysUserCount createGetSysUserCount() {
        return new GetSysUserCount();
    }

    /**
     * Create an instance of {@link GetSysUserCountResponse }
     * 
     */
    public GetSysUserCountResponse createGetSysUserCountResponse() {
        return new GetSysUserCountResponse();
    }

    /**
     * Create an instance of {@link GetSysUserList }
     * 
     */
    public GetSysUserList createGetSysUserList() {
        return new GetSysUserList();
    }

    /**
     * Create an instance of {@link GetSysUserListResponse }
     * 
     */
    public GetSysUserListResponse createGetSysUserListResponse() {
        return new GetSysUserListResponse();
    }

    /**
     * Create an instance of {@link SysUser }
     * 
     */
    public SysUser createSysUser() {
        return new SysUser();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSysUserCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.sys.cxf.facade.servers.its.com/", name = "getSysUserCount")
    public JAXBElement<GetSysUserCount> createGetSysUserCount(GetSysUserCount value) {
        return new JAXBElement<GetSysUserCount>(_GetSysUserCount_QNAME, GetSysUserCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSysUserCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.sys.cxf.facade.servers.its.com/", name = "getSysUserCountResponse")
    public JAXBElement<GetSysUserCountResponse> createGetSysUserCountResponse(GetSysUserCountResponse value) {
        return new JAXBElement<GetSysUserCountResponse>(_GetSysUserCountResponse_QNAME, GetSysUserCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSysUserList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.sys.cxf.facade.servers.its.com/", name = "getSysUserList")
    public JAXBElement<GetSysUserList> createGetSysUserList(GetSysUserList value) {
        return new JAXBElement<GetSysUserList>(_GetSysUserList_QNAME, GetSysUserList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSysUserListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.sys.cxf.facade.servers.its.com/", name = "getSysUserListResponse")
    public JAXBElement<GetSysUserListResponse> createGetSysUserListResponse(GetSysUserListResponse value) {
        return new JAXBElement<GetSysUserListResponse>(_GetSysUserListResponse_QNAME, GetSysUserListResponse.class, null, value);
    }

}
