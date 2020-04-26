package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysMenuDO;
import com.zjft.microservice.treasurybrain.common.dto.MenuDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:07:47+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
public class SysMenuConverterImpl implements SysMenuConverter {

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
    public SysMenuDO dto2do(MenuDTO domain) {
        if ( domain == null ) {
            return null;
        }

        SysMenuDO sysMenuDO = new SysMenuDO();

        sysMenuDO.setMenuSize( domain.getSize() );
        sysMenuDO.setMenuIcon( domain.getIcon() );
        sysMenuDO.setMenuBg( domain.getBackgroundColor() );
        sysMenuDO.setNo( domain.getNo() );
        sysMenuDO.setName( domain.getName() );
        sysMenuDO.setMenuFather( domain.getMenuFather() );
        sysMenuDO.setUrl( domain.getUrl() );
        sysMenuDO.setMenuOrder( domain.getMenuOrder() );
        sysMenuDO.setNote( domain.getNote() );
        sysMenuDO.setButtonTag( domain.getButtonTag() );
        sysMenuDO.setButton( domain.getButton() );
        sysMenuDO.setChecked( domain.getChecked() );

        return sysMenuDO;
    }

    @Override
    public List<MenuDTO> domain2dto(List<SysMenuDO> domain) {
        if ( domain == null ) {
            return null;
        }

        List<MenuDTO> list = new ArrayList<MenuDTO>( domain.size() );
        for ( SysMenuDO sysMenuDO : domain ) {
            list.add( domain2dto( sysMenuDO ) );
        }

        return list;
    }

    @Override
    public List<SysMenuDO> dto2do(List<MenuDTO> domain) {
        if ( domain == null ) {
            return null;
        }

        List<SysMenuDO> list = new ArrayList<SysMenuDO>( domain.size() );
        for ( MenuDTO menuDTO : domain ) {
            list.add( dto2do( menuDTO ) );
        }

        return list;
    }
}
