import 'package:chopper/chopper.dart';
import 'package:flutter_module/model/cities.dart';
import 'package:flutter_module/service/model_converter.dart';
import 'package:flutter_module/utils/constants.dart';

part 'cities_service.chopper.dart';

@ChopperApi()
abstract class CitiesService extends ChopperService {
  @Get(path: 'cities')
  Future<Response<PopularCities>> getPopularCities();

  static CitiesService create() {
    final client = ChopperClient(
      baseUrl: SERVER_URL,
      interceptors: [HttpLoggingInterceptor()],
      converter: ModelConverter(
          {PopularCities: (json) => PopularCities.fromJson(json)}),
      errorConverter: JsonConverter(),
      services: [
        _$CitiesService(),
      ],
    );

    return _$CitiesService(client);
  }
}
