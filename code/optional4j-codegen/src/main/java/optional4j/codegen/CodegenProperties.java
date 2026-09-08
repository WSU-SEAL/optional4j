package optional4j.codegen;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import optional4j.support.ModeValue;
import optional4j.support.NullabilityValue;

@RequiredArgsConstructor
@Getter
@ToString
public class CodegenProperties {

    @NonNull private final NullabilityValue nullity;

    @NonNull private final ModeValue mode;

    private final boolean nullityEnabled;

    private final boolean enhancedSyntax;
}
