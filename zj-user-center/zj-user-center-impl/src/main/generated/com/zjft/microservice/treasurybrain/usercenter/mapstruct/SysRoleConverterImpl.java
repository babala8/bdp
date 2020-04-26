package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysRoleDO;
import com.zjft.microservice.treasurybrain.common.dto.RoleDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:07:46+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
public class SysRoleConverterImpl implements SysRoleConverter {

    @Override
    public RoleDTO domain2dto(SysRoleDO domain) {
        if ( domain == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setNo( domain.getNo() );
        roleDTO.setName( domain.getName() );
        roleDTO.setCatalog( domain.getCatalog() );
        roleDTO.setOrgGradeNo( domain.getOrgGradeNo() );
        roleDTO.setNote( domain.getNote() );

        return roleDTO;
    }

    @Override
    public SysRoleDO dto2do(RoleDTO domain) {
        if ( domain == null ) {
            return null;
        }

        SysRoleDO sysRoleDO = new SysRoleDO();

        sysRoleDO.setNo( domain.getNo() );
        sysRoleDO.setName( domain.getName() );
        sysRoleDO.setCatalog( domain.getCatalog() );
        sysRoleDO.setOrgGradeNo( domain.getOrgGradeNo() );
        sysRoleDO.setNote( domain.getNote() );

        return sysRoleDO;
    }

    @Override
    public List<SysRoleDO> dto2do(List<RoleDTO> domain) {
        if ( domain == null ) {
            return null;
        }

        List<SysRoleDO> list = new ArrayList<SysRoleDO>( domain.size() );
        for ( RoleDTO roleDTO : domain ) {
            list.add( dto2do( roleDTO ) );
        }

        return list;
    }

    @Override
    public List<RoleDTO> domain2dto(List<SysRoleDO> domain) {
        if ( domain == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( domain.size() );
        for ( SysRoleDO sysRoleDO : domain ) {
            list.add( domain2dto( sysRoleDO ) );
        }

        return list;
    }
}
