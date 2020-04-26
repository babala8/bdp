package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import com.zjft.microservice.treasurybrain.common.domain.SysOrgGrade;
import com.zjft.microservice.treasurybrain.common.domain.SysPostDO;
import com.zjft.microservice.treasurybrain.common.domain.SysRoleDO;
import com.zjft.microservice.treasurybrain.common.domain.SysUserDO;
import com.zjft.microservice.treasurybrain.common.domain.SysUserPostDO;
import com.zjft.microservice.treasurybrain.common.dto.ClrCenterDTO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;
import com.zjft.microservice.treasurybrain.common.dto.RoleDTO;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgGradeDTO;
import com.zjft.microservice.treasurybrain.common.dto.SysUserPostDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:07:48+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
public class SysUserConverterImpl implements SysUserConverter {

    @Override
    public UserDTO domain2dto(SysUserDO domain) {
        if ( domain == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername( domain.getUsername() );
        userDTO.setName( domain.getName() );
        userDTO.setStatus( domain.getStatus() );
        userDTO.setOnlineFlag( domain.getOnlineFlag() );
        userDTO.setOrgNo( domain.getOrgNo() );
        userDTO.setPhone( domain.getPhone() );
        userDTO.setMobile( domain.getMobile() );
        userDTO.setEmail( domain.getEmail() );
        userDTO.setPhoto( domain.getPhoto() );
        userDTO.setGroupType( domain.getGroupType() );
        userDTO.setServiceCompany( domain.getServiceCompany() );
        userDTO.setRoleList( sysRoleDOListToRoleDTOList( domain.getRoleList() ) );
        userDTO.setPostList( sysUserPostDOListToSysUserPostDTOList( domain.getPostList() ) );
        List<SysPostDO> list2 = domain.getPostDetailList();
        if ( list2 != null ) {
            userDTO.setPostDetailList( new ArrayList<SysPostDO>( list2 ) );
        }
        userDTO.setSysOrg( domain2dto( domain.getSysOrg() ) );
        userDTO.setMenuList( sysMenuDOListToMenuDTOList( domain.getMenuList() ) );

        return userDTO;
    }

    @Override
    public SysUserDO dto2do(UserDTO domain) {
        if ( domain == null ) {
            return null;
        }

        SysUserDO sysUserDO = new SysUserDO();

        sysUserDO.setUsername( domain.getUsername() );
        sysUserDO.setPassword( domain.getPassword() );
        sysUserDO.setName( domain.getName() );
        sysUserDO.setOnlineFlag( domain.getOnlineFlag() );
        sysUserDO.setOrgNo( domain.getOrgNo() );
        sysUserDO.setPhone( domain.getPhone() );
        sysUserDO.setMobile( domain.getMobile() );
        sysUserDO.setEmail( domain.getEmail() );
        sysUserDO.setPhoto( domain.getPhoto() );
        sysUserDO.setGroupType( domain.getGroupType() );
        sysUserDO.setServiceCompany( domain.getServiceCompany() );
        sysUserDO.setSysOrg( sysOrgDTOToSysOrg( domain.getSysOrg() ) );
        sysUserDO.setRoleList( roleDTOListToSysRoleDOList( domain.getRoleList() ) );
        sysUserDO.setPostList( sysUserPostDTOListToSysUserPostDOList( domain.getPostList() ) );
        List<SysPostDO> list2 = domain.getPostDetailList();
        if ( list2 != null ) {
            sysUserDO.setPostDetailList( new ArrayList<SysPostDO>( list2 ) );
        }
        sysUserDO.setMenuList( menuDTOListToSysMenuDOList( domain.getMenuList() ) );

        return sysUserDO;
    }

    @Override
    public MenuDTO domain2dto(SysMenuDO domain) {
        if ( domain == null ) {
            return null;
        }

        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setIcon( domain.getMenuIcon() );
        menuDTO.setBackgroundColor( domain.getMenuBg() );
        menuDTO.setSize( domain.getMenuSize() );
        menuDTO.setNo( domain.getNo() );
        menuDTO.setName( domain.getName() );
        menuDTO.setMenuFather( domain.getMenuFather() );
        menuDTO.setUrl( domain.getUrl() );
        menuDTO.setMenuOrder( domain.getMenuOrder() );
        menuDTO.setNote( domain.getNote() );
        menuDTO.setButtonTag( domain.getButtonTag() );
        menuDTO.setButton( domain.getButton() );
        menuDTO.setChecked( domain.getChecked() );

        return menuDTO;
    }

    @Override
    public List<UserDTO> domain2dto(List<SysUserDO> domain) {
        if ( domain == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( domain.size() );
        for ( SysUserDO sysUserDO : domain ) {
            list.add( domain2dto( sysUserDO ) );
        }

        return list;
    }

    @Override
    public List<SysUserDO> dto2do(List<UserDTO> domain) {
        if ( domain == null ) {
            return null;
        }

        List<SysUserDO> list = new ArrayList<SysUserDO>( domain.size() );
        for ( UserDTO userDTO : domain ) {
            list.add( dto2do( userDTO ) );
        }

        return list;
    }

    @Override
    public SysOrgDTO domain2dto(SysOrg domain) {
        if ( domain == null ) {
            return null;
        }

        SysOrgDTO sysOrgDTO = new SysOrgDTO();

        sysOrgDTO.setNo( domain.getNo() );
        sysOrgDTO.setName( domain.getName() );
        sysOrgDTO.setParentOrgNo( domain.getParentOrgNo() );
        sysOrgDTO.setParentOrg( domain2dto( domain.getParentOrg() ) );
        sysOrgDTO.setLeft( domain.getLeft() );
        sysOrgDTO.setRight( domain.getRight() );
        sysOrgDTO.setMoneyorgFlag( domain.getMoneyorgFlag() );
        sysOrgDTO.setOrgGradeNo( domain.getOrgGradeNo() );
        sysOrgDTO.setOrgGrade( sysOrgGradeToSysOrgGradeDTO( domain.getOrgGrade() ) );
        sysOrgDTO.setStatus( domain.getStatus() );
        sysOrgDTO.setRegion( domain.getRegion() );
        sysOrgDTO.setCity( domain.getCity() );
        sysOrgDTO.setX( domain.getX() );
        sysOrgDTO.setY( domain.getY() );
        sysOrgDTO.setLinkman( domain.getLinkman() );
        sysOrgDTO.setAddress( domain.getAddress() );
        sysOrgDTO.setTelephone( domain.getTelephone() );
        sysOrgDTO.setMobile( domain.getMobile() );
        sysOrgDTO.setFax( domain.getFax() );
        sysOrgDTO.setEmail( domain.getEmail() );
        sysOrgDTO.setBusinessRange( domain.getBusinessRange() );
        sysOrgDTO.setCupAreaCode( domain.getCupAreaCode() );
        sysOrgDTO.setAddressCode( domain.getAddressCode() );
        sysOrgDTO.setAreaNo( domain.getAreaNo() );
        sysOrgDTO.setAreaType( domain.getAreaType() );
        sysOrgDTO.setOrgPhysicsCatalog( domain.getOrgPhysicsCatalog() );
        sysOrgDTO.setClrCenterNo( domain.getClrCenterNo() );
        sysOrgDTO.setClrCenter( clrCenterTableToClrCenterDTO( domain.getClrCenter() ) );
        sysOrgDTO.setClrCenterFlag( domain.getClrCenterFlag() );
        sysOrgDTO.setClrCenterNoCash( domain.getClrCenterNoCash() );
        sysOrgDTO.setShortName( domain.getShortName() );
        sysOrgDTO.setFullName( domain.getFullName() );
        sysOrgDTO.setAwayFlag( domain.getAwayFlag() );
        sysOrgDTO.setNote( domain.getNote() );
        sysOrgDTO.setDeliveryTime( domain.getDeliveryTime() );
        sysOrgDTO.setBackTime( domain.getBackTime() );

        return sysOrgDTO;
    }

    protected RoleDTO sysRoleDOToRoleDTO(SysRoleDO sysRoleDO) {
        if ( sysRoleDO == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setNo( sysRoleDO.getNo() );
        roleDTO.setName( sysRoleDO.getName() );
        roleDTO.setCatalog( sysRoleDO.getCatalog() );
        roleDTO.setOrgGradeNo( sysRoleDO.getOrgGradeNo() );
        roleDTO.setNote( sysRoleDO.getNote() );

        return roleDTO;
    }

    protected List<RoleDTO> sysRoleDOListToRoleDTOList(List<SysRoleDO> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleDTO> list1 = new ArrayList<RoleDTO>( list.size() );
        for ( SysRoleDO sysRoleDO : list ) {
            list1.add( sysRoleDOToRoleDTO( sysRoleDO ) );
        }

        return list1;
    }

    protected SysUserPostDTO sysUserPostDOToSysUserPostDTO(SysUserPostDO sysUserPostDO) {
        if ( sysUserPostDO == null ) {
            return null;
        }

        SysUserPostDTO sysUserPostDTO = new SysUserPostDTO();

        sysUserPostDTO.setUserName( sysUserPostDO.getUserName() );
        sysUserPostDTO.setPostNo( sysUserPostDO.getPostNo() );

        return sysUserPostDTO;
    }

    protected List<SysUserPostDTO> sysUserPostDOListToSysUserPostDTOList(List<SysUserPostDO> list) {
        if ( list == null ) {
            return null;
        }

        List<SysUserPostDTO> list1 = new ArrayList<SysUserPostDTO>( list.size() );
        for ( SysUserPostDO sysUserPostDO : list ) {
            list1.add( sysUserPostDOToSysUserPostDTO( sysUserPostDO ) );
        }

        return list1;
    }

    protected List<MenuDTO> sysMenuDOListToMenuDTOList(List<SysMenuDO> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuDTO> list1 = new ArrayList<MenuDTO>( list.size() );
        for ( SysMenuDO sysMenuDO : list ) {
            list1.add( domain2dto( sysMenuDO ) );
        }

        return list1;
    }

    protected SysOrgGrade sysOrgGradeDTOToSysOrgGrade(SysOrgGradeDTO sysOrgGradeDTO) {
        if ( sysOrgGradeDTO == null ) {
            return null;
        }

        SysOrgGrade sysOrgGrade = new SysOrgGrade();

        sysOrgGrade.setNo( sysOrgGradeDTO.getNo() );
        sysOrgGrade.setName( sysOrgGradeDTO.getName() );
        sysOrgGrade.setOrgType( sysOrgGradeDTO.getOrgType() );

        return sysOrgGrade;
    }

    protected ClrCenterTable clrCenterDTOToClrCenterTable(ClrCenterDTO clrCenterDTO) {
        if ( clrCenterDTO == null ) {
            return null;
        }

        ClrCenterTable clrCenterTable = new ClrCenterTable();

        clrCenterTable.setClrCenterNo( clrCenterDTO.getClrCenterNo() );
        clrCenterTable.setCenterName( clrCenterDTO.getCenterName() );
        clrCenterTable.setBankOrgNo( clrCenterDTO.getBankOrgNo() );
        clrCenterTable.setNetpointMatrixStatus( clrCenterDTO.getNetpointMatrixStatus() );
        clrCenterTable.setCashtruckNum( clrCenterDTO.getCashtruckNum() );
        clrCenterTable.setAutoFlag( clrCenterDTO.getAutoFlag() );
        clrCenterTable.setCenterType( clrCenterDTO.getCenterType() );
        clrCenterTable.setLineMode( clrCenterDTO.getLineMode() );
        clrCenterTable.setNote( clrCenterDTO.getNote() );
        clrCenterTable.setNetpointMatrixStatusOrg( clrCenterDTO.getNetpointMatrixStatusOrg() );
        clrCenterTable.setCostMatrixPointType( clrCenterDTO.getCostMatrixPointType() );
        clrCenterTable.setSysOrg( sysOrgDTOToSysOrg( clrCenterDTO.getSysOrg() ) );

        return clrCenterTable;
    }

    protected SysOrg sysOrgDTOToSysOrg(SysOrgDTO sysOrgDTO) {
        if ( sysOrgDTO == null ) {
            return null;
        }

        SysOrg sysOrg = new SysOrg();

        sysOrg.setNo( sysOrgDTO.getNo() );
        sysOrg.setName( sysOrgDTO.getName() );
        sysOrg.setParentOrgNo( sysOrgDTO.getParentOrgNo() );
        sysOrg.setParentOrg( sysOrgDTOToSysOrg( sysOrgDTO.getParentOrg() ) );
        sysOrg.setLeft( sysOrgDTO.getLeft() );
        sysOrg.setRight( sysOrgDTO.getRight() );
        sysOrg.setOrgGradeNo( sysOrgDTO.getOrgGradeNo() );
        sysOrg.setOrgGrade( sysOrgGradeDTOToSysOrgGrade( sysOrgDTO.getOrgGrade() ) );
        sysOrg.setMoneyorgFlag( sysOrgDTO.getMoneyorgFlag() );
        sysOrg.setX( sysOrgDTO.getX() );
        sysOrg.setY( sysOrgDTO.getY() );
        sysOrg.setAddress( sysOrgDTO.getAddress() );
        sysOrg.setLinkman( sysOrgDTO.getLinkman() );
        sysOrg.setTelephone( sysOrgDTO.getTelephone() );
        sysOrg.setMobile( sysOrgDTO.getMobile() );
        sysOrg.setFax( sysOrgDTO.getFax() );
        sysOrg.setEmail( sysOrgDTO.getEmail() );
        sysOrg.setBusinessRange( sysOrgDTO.getBusinessRange() );
        sysOrg.setCupAreaCode( sysOrgDTO.getCupAreaCode() );
        sysOrg.setAddressCode( sysOrgDTO.getAddressCode() );
        sysOrg.setAreaNo( sysOrgDTO.getAreaNo() );
        sysOrg.setAreaType( sysOrgDTO.getAreaType() );
        sysOrg.setOrgPhysicsCatalog( sysOrgDTO.getOrgPhysicsCatalog() );
        sysOrg.setNote( sysOrgDTO.getNote() );
        sysOrg.setClrCenterNo( sysOrgDTO.getClrCenterNo() );
        sysOrg.setClrCenter( clrCenterDTOToClrCenterTable( sysOrgDTO.getClrCenter() ) );
        sysOrg.setCity( sysOrgDTO.getCity() );
        sysOrg.setRegion( sysOrgDTO.getRegion() );
        sysOrg.setStatus( sysOrgDTO.getStatus() );
        sysOrg.setClrCenterFlag( sysOrgDTO.getClrCenterFlag() );
        sysOrg.setShortName( sysOrgDTO.getShortName() );
        sysOrg.setFullName( sysOrgDTO.getFullName() );
        sysOrg.setAwayFlag( sysOrgDTO.getAwayFlag() );
        sysOrg.setClrCenterNoCash( sysOrgDTO.getClrCenterNoCash() );
        sysOrg.setDeliveryTime( sysOrgDTO.getDeliveryTime() );
        sysOrg.setBackTime( sysOrgDTO.getBackTime() );

        return sysOrg;
    }

    protected SysRoleDO roleDTOToSysRoleDO(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        SysRoleDO sysRoleDO = new SysRoleDO();

        sysRoleDO.setNo( roleDTO.getNo() );
        sysRoleDO.setName( roleDTO.getName() );
        sysRoleDO.setCatalog( roleDTO.getCatalog() );
        sysRoleDO.setOrgGradeNo( roleDTO.getOrgGradeNo() );
        sysRoleDO.setNote( roleDTO.getNote() );

        return sysRoleDO;
    }

    protected List<SysRoleDO> roleDTOListToSysRoleDOList(List<RoleDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SysRoleDO> list1 = new ArrayList<SysRoleDO>( list.size() );
        for ( RoleDTO roleDTO : list ) {
            list1.add( roleDTOToSysRoleDO( roleDTO ) );
        }

        return list1;
    }

    protected SysUserPostDO sysUserPostDTOToSysUserPostDO(SysUserPostDTO sysUserPostDTO) {
        if ( sysUserPostDTO == null ) {
            return null;
        }

        SysUserPostDO sysUserPostDO = new SysUserPostDO();

        sysUserPostDO.setUserName( sysUserPostDTO.getUserName() );
        sysUserPostDO.setPostNo( sysUserPostDTO.getPostNo() );

        return sysUserPostDO;
    }

    protected List<SysUserPostDO> sysUserPostDTOListToSysUserPostDOList(List<SysUserPostDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SysUserPostDO> list1 = new ArrayList<SysUserPostDO>( list.size() );
        for ( SysUserPostDTO sysUserPostDTO : list ) {
            list1.add( sysUserPostDTOToSysUserPostDO( sysUserPostDTO ) );
        }

        return list1;
    }

    protected SysMenuDO menuDTOToSysMenuDO(MenuDTO menuDTO) {
        if ( menuDTO == null ) {
            return null;
        }

        SysMenuDO sysMenuDO = new SysMenuDO();

        sysMenuDO.setNo( menuDTO.getNo() );
        sysMenuDO.setName( menuDTO.getName() );
        sysMenuDO.setMenuFather( menuDTO.getMenuFather() );
        sysMenuDO.setUrl( menuDTO.getUrl() );
        sysMenuDO.setMenuOrder( menuDTO.getMenuOrder() );
        sysMenuDO.setNote( menuDTO.getNote() );
        sysMenuDO.setButtonTag( menuDTO.getButtonTag() );
        sysMenuDO.setButton( menuDTO.getButton() );
        sysMenuDO.setChecked( menuDTO.getChecked() );

        return sysMenuDO;
    }

    protected List<SysMenuDO> menuDTOListToSysMenuDOList(List<MenuDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SysMenuDO> list1 = new ArrayList<SysMenuDO>( list.size() );
        for ( MenuDTO menuDTO : list ) {
            list1.add( menuDTOToSysMenuDO( menuDTO ) );
        }

        return list1;
    }

    protected SysOrgGradeDTO sysOrgGradeToSysOrgGradeDTO(SysOrgGrade sysOrgGrade) {
        if ( sysOrgGrade == null ) {
            return null;
        }

        SysOrgGradeDTO sysOrgGradeDTO = new SysOrgGradeDTO();

        sysOrgGradeDTO.setNo( sysOrgGrade.getNo() );
        sysOrgGradeDTO.setName( sysOrgGrade.getName() );
        sysOrgGradeDTO.setOrgType( sysOrgGrade.getOrgType() );

        return sysOrgGradeDTO;
    }

    protected ClrCenterDTO clrCenterTableToClrCenterDTO(ClrCenterTable clrCenterTable) {
        if ( clrCenterTable == null ) {
            return null;
        }

        ClrCenterDTO clrCenterDTO = new ClrCenterDTO();

        clrCenterDTO.setClrCenterNo( clrCenterTable.getClrCenterNo() );
        clrCenterDTO.setCenterName( clrCenterTable.getCenterName() );
        clrCenterDTO.setBankOrgNo( clrCenterTable.getBankOrgNo() );
        clrCenterDTO.setNote( clrCenterTable.getNote() );
        clrCenterDTO.setAutoFlag( clrCenterTable.getAutoFlag() );
        clrCenterDTO.setCenterType( clrCenterTable.getCenterType() );
        clrCenterDTO.setLineMode( clrCenterTable.getLineMode() );
        clrCenterDTO.setNetpointMatrixStatus( clrCenterTable.getNetpointMatrixStatus() );
        clrCenterDTO.setCashtruckNum( clrCenterTable.getCashtruckNum() );
        clrCenterDTO.setNetpointMatrixStatusOrg( clrCenterTable.getNetpointMatrixStatusOrg() );
        clrCenterDTO.setCostMatrixPointType( clrCenterTable.getCostMatrixPointType() );
        clrCenterDTO.setSysOrg( domain2dto( clrCenterTable.getSysOrg() ) );

        return clrCenterDTO;
    }
}
