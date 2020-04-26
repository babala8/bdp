package com.zjft.microservice.treasurybrain.usercenter.mapstruct;

import com.zjft.microservice.treasurybrain.common.domain.SysUserPostDO;
import com.zjft.microservice.treasurybrain.common.dto.SysUserPostDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:07:47+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
public class SysUserPostConverterImpl implements SysUserPostConverter {

    @Override
    public SysUserPostDO dto2do(SysUserPostDTO sysUserPostDTO) {
        if ( sysUserPostDTO == null ) {
            return null;
        }

        SysUserPostDO sysUserPostDO = new SysUserPostDO();

        sysUserPostDO.setUserName( sysUserPostDTO.getUserName() );
        sysUserPostDO.setPostNo( sysUserPostDTO.getPostNo() );

        return sysUserPostDO;
    }

    @Override
    public SysUserPostDTO do2dto(SysUserPostDO sysUserPostDO) {
        if ( sysUserPostDO == null ) {
            return null;
        }

        SysUserPostDTO sysUserPostDTO = new SysUserPostDTO();

        sysUserPostDTO.setUserName( sysUserPostDO.getUserName() );
        sysUserPostDTO.setPostNo( sysUserPostDO.getPostNo() );

        return sysUserPostDTO;
    }

    @Override
    public List<SysUserPostDO> dto2do(List<SysUserPostDTO> sysUserPostDTOList) {
        if ( sysUserPostDTOList == null ) {
            return null;
        }

        List<SysUserPostDO> list = new ArrayList<SysUserPostDO>( sysUserPostDTOList.size() );
        for ( SysUserPostDTO sysUserPostDTO : sysUserPostDTOList ) {
            list.add( dto2do( sysUserPostDTO ) );
        }

        return list;
    }

    @Override
    public List<SysUserPostDTO> do2dto(List<SysUserPostDO> sysUserPostDOList) {
        if ( sysUserPostDOList == null ) {
            return null;
        }

        List<SysUserPostDTO> list = new ArrayList<SysUserPostDTO>( sysUserPostDOList.size() );
        for ( SysUserPostDO sysUserPostDO : sysUserPostDOList ) {
            list.add( do2dto( sysUserPostDO ) );
        }

        return list;
    }
}
