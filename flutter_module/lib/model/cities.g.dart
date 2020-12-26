// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'cities.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PopularCities _$PopularCitiesFromJson(Map<String, dynamic> json) {
  return PopularCities(
    (json['data'] as List)?.map((e) => e as String)?.toList(),
  );
}

Map<String, dynamic> _$PopularCitiesToJson(PopularCities instance) =>
    <String, dynamic>{
      'data': instance.data,
    };
