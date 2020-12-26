// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'cities_service.dart';

// **************************************************************************
// ChopperGenerator
// **************************************************************************

// ignore_for_file: always_put_control_body_on_new_line, always_specify_types, prefer_const_declarations
class _$CitiesService extends CitiesService {
  _$CitiesService([ChopperClient client]) {
    if (client == null) return;
    this.client = client;
  }

  @override
  final definitionType = CitiesService;

  @override
  Future<Response<PopularCities>> getPopularCities() {
    final $url = 'cities';
    final $request = Request('GET', $url, client.baseUrl);
    return client.send<PopularCities, PopularCities>($request);
  }
}
