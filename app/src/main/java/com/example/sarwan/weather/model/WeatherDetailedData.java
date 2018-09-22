package com.example.sarwan.weather.model;

import java.util.List;

public class WeatherDetailedData {
    private String cod;
    private double message;
    private int cnt;
    private City city;
    private List<DataLiist> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<DataLiist> getList() {
        return list;
    }

    public void setList(List<DataLiist> list) {
        this.list = list;
    }

    public class City {
        private long id;
        private String name;
        private String country;
        private String population;
        private Coord coord;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPopulation() {
            return population;
        }

        public void setPopulation(String population) {
            this.population = population;
        }

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public class Coord {

            private Double lon;
            private Double lat;

            public Double getLon() {
                return lon;
            }

            public void setLon(Double lon) {
                this.lon = lon;
            }

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }

        }
    }

    public class DataLiist {
        private Integer dt;
        private Main main;
        private List<Weather> weather;
        private Clouds clouds;
        private Wind wind;
        private Sys sys;
        private String dt_txt;

        public Integer getDt() {
            return dt;
        }

        public void setDt(Integer dt) {
            this.dt = dt;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }

        public class Main {

            private Double temp;
            private Double sea_level;
            private Double grnd_level;
            private Double temp_kf;
            private Integer humidity;
            private Double pressure;
            private Double temp_min;
            private Double temp_max;

            public Double getSea_level() {
                return sea_level;
            }

            public void setSea_level(Double sea_level) {
                this.sea_level = sea_level;
            }

            public Double getGrnd_level() {
                return grnd_level;
            }

            public void setGrnd_level(Double grnd_level) {
                this.grnd_level = grnd_level;
            }

            public Double getTemp_kf() {
                return temp_kf;
            }

            public void setTemp_kf(Double temp_kf) {
                this.temp_kf = temp_kf;
            }

            public Double getTemp() {
                return temp;
            }

            public void setTemp(Double temp) {
                this.temp = temp;
            }

            public Integer getHumidity() {
                return humidity;
            }

            public void setHumidity(Integer humidity) {
                this.humidity = humidity;
            }

            public Double getPressure() {
                return pressure;
            }

            public void setPressure(Double pressure) {
                this.pressure = pressure;
            }

            public Double getTemp_min() {
                return temp_min;
            }

            public void setTemp_min(Double temp_min) {
                this.temp_min = temp_min;
            }

            public Double getTemp_max() {
                return temp_max;
            }

            public void setTemp_max(Double temp_max) {
                this.temp_max = temp_max;
            }

        }

        public class Weather {

            private Integer id;
            private String main;
            private String description;
            private String icon;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

        }

        public class Clouds {

            private Integer all;

            public Integer getAll() {
                return all;
            }

            public void setAll(Integer all) {
                this.all = all;
            }

        }

        public class Wind {

            private Double speed;
            private Double deg;

            public Double getSpeed() {
                return speed;
            }

            public void setSpeed(Double speed) {
                this.speed = speed;
            }

            public Double getDeg() {
                return deg;
            }

            public void setDeg(Double deg) {
                this.deg = deg;
            }

        }

        public class Sys {

            private String pod;

            public String getPod() {
                return pod;
            }

            public void setPod(String pod) {
                this.pod = pod;
            }
        }

    }


}