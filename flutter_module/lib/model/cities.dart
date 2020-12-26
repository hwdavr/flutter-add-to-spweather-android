import 'package:json_annotation/json_annotation.dart';
import 'dart:core';

part 'cities.g.dart';

@JsonSerializable()
class PopularCities {

  @JsonKey(name: 'data')
  List<String> data;

  PopularCities(this.data);

  factory PopularCities.fromJson(Map<String, dynamic> json) => _$PopularCitiesFromJson(json);

  Map<String, dynamic> toJson() => _$PopularCitiesToJson(this);
}
