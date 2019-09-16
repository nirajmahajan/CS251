cmake_minimum_required(VERSION 3.1.0)

project(mygamestatic)
add_executable(mygamestatic mygame.cpp)
link_directories(usr/local/lib)
target_link_libraries(mygamestatic MyEngineStatic)

project(mygamedynamic)
add_executable(mygamedynamic mygame.cpp)
link_directories(usr/local/lib)
target_link_libraries(mygamedynamic MyEngineDynamic)
