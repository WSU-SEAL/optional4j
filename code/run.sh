#binbash
java -XX:+DoEscapeAnalysis -XX:-Inline -jar ./optional4j-performance-test/target/benchmarks.jar -bm Throughput -f 2 -foe true -i 4 -t 1 -wi 2 -tu s -prof gc >> _plus_escape.txt
java -XX:-DoEscapeAnalysis +XX:-Inline -jar ./optional4j-performance-test/target/benchmarks.jar -bm Throughput -f 2 -foe true -i 4 -t 1 -wi 2 -tu s -prof gc >> _plus_inline.txt
java -XX:-DoEscapeAnalysis -XX:-Inline -jar ./optional4j-performance-test/target/benchmarks.jar -bm Throughput -f 2 -foe true -i 4 -t 1 -wi 2 -tu s -prof gc >> _both_disabled.txt
java -XX:+DoEscapeAnalysis +XX:-Inline -jar ./optional4j-performance-test/target/benchmarks.jar -bm Throughput -f 2 -foe true -i 4 -t 1 -wi 2 -tu s -prof gc >> _both_enabled.txt
