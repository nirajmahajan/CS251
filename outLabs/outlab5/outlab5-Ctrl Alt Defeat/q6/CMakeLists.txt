cmake_minimum_required (VERSION 3.1.0)
set(CMAKE_CXX_STANDARD 14)

add_executable (helloworld helloworld.cpp)
add_executable (usespthread usespthread.cpp)

set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)
target_link_libraries(usespthread Threads::Threads)
set(THREADS_PREFER_PTHREAD_FLAG OFF)

add_library (MyEngineStatic STATIC myengine/myengine.cpp)
add_library (MyEngineDynamic SHARED myengine/myengine.cpp)

install (TARGETS MyEngineStatic DESTINATION "/usr/local/lib")
install (TARGETS MyEngineDynamic DESTINATION "/usr/local/lib")
install (FILES myengine/myengine.hpp DESTINATION "/usr/local/include")
