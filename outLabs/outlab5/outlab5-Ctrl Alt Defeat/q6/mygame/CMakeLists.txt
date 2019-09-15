cmake_minimum_required (VERSION 3.1.0)

include_directories("${CMAKE_BINARY_DIR}/../build")

add_executable (mygamedynamic mygame.cpp)
add_executable (mygamestatic mygame.cpp)

target_link_libraries (mygamedynamic MyEngineDynamic)
target_link_libraries (mygamestatic MyEngineStatic)