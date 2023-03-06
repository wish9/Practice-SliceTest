package com.codestates.coffee.mapper;

import com.codestates.coffee.dto.CoffeePatchDto;
import com.codestates.coffee.dto.CoffeePostDto;
import com.codestates.coffee.dto.CoffeeResponseDto;
import com.codestates.coffee.entity.Coffee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-06T15:22:21+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class CoffeeMapperImpl implements CoffeeMapper {

    @Override
    public Coffee coffeePostDtoToCoffee(CoffeePostDto coffeePostDto) {
        if ( coffeePostDto == null ) {
            return null;
        }

        Coffee coffee = new Coffee();

        coffee.setKorName( coffeePostDto.getKorName() );
        coffee.setEngName( coffeePostDto.getEngName() );
        coffee.setPrice( coffeePostDto.getPrice() );
        coffee.setCoffeeCode( coffeePostDto.getCoffeeCode() );

        return coffee;
    }

    @Override
    public Coffee coffeePatchDtoToCoffee(CoffeePatchDto coffeePatchDto) {
        if ( coffeePatchDto == null ) {
            return null;
        }

        Coffee coffee = new Coffee();

        coffee.setCoffeeId( coffeePatchDto.getCoffeeId() );
        coffee.setKorName( coffeePatchDto.getKorName() );
        coffee.setEngName( coffeePatchDto.getEngName() );
        coffee.setPrice( coffeePatchDto.getPrice() );
        coffee.setCoffeeStatus( coffeePatchDto.getCoffeeStatus() );

        return coffee;
    }

    @Override
    public CoffeeResponseDto coffeeToCoffeeResponseDto(Coffee coffee) {
        if ( coffee == null ) {
            return null;
        }

        CoffeeResponseDto.CoffeeResponseDtoBuilder coffeeResponseDto = CoffeeResponseDto.builder();

        if ( coffee.getCoffeeId() != null ) {
            coffeeResponseDto.coffeeId( coffee.getCoffeeId() );
        }
        coffeeResponseDto.korName( coffee.getKorName() );
        coffeeResponseDto.engName( coffee.getEngName() );
        if ( coffee.getPrice() != null ) {
            coffeeResponseDto.price( coffee.getPrice() );
        }
        coffeeResponseDto.coffeeStatus( coffee.getCoffeeStatus() );

        return coffeeResponseDto.build();
    }

    @Override
    public List<CoffeeResponseDto> coffeesToCoffeeResponseDtos(List<Coffee> coffees) {
        if ( coffees == null ) {
            return null;
        }

        List<CoffeeResponseDto> list = new ArrayList<CoffeeResponseDto>( coffees.size() );
        for ( Coffee coffee : coffees ) {
            list.add( coffeeToCoffeeResponseDto( coffee ) );
        }

        return list;
    }
}
