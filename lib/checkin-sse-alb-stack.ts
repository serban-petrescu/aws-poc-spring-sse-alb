import { Stack, Construct, StackProps } from "@aws-cdk/core";
import { Vpc } from "@aws-cdk/aws-ec2";
import { Cluster, ContainerImage } from "@aws-cdk/aws-ecs";
import { ApplicationProtocol } from "@aws-cdk/aws-elasticloadbalancingv2";
import { ApplicationLoadBalancedFargateService } from "@aws-cdk/aws-ecs-patterns";
import { join } from "path";

export class CheckinSseAlbStack extends Stack {
    constructor(scope: Construct, id: string, props: StackProps) {
        super(scope, id, props);

        const vpc = new Vpc(this, "Vpc", {
            natGateways: 0,
        });

        const cluster = new Cluster(this, "Cluster", {
            vpc,
        });

        const service = new ApplicationLoadBalancedFargateService(
            this,
            "Service",
            {
                cluster,
                desiredCount: 1,
                protocol: ApplicationProtocol.HTTP,
                cpu: 1024,
                memoryLimitMiB: 2048,
                assignPublicIp: true,
                taskImageOptions: {
                    image: ContainerImage.fromAsset(join(__dirname, '..', 'app')),
                    containerPort: 8080,
                },
            }
        );

        service.targetGroup.configureHealthCheck({
            healthyHttpCodes: '200-399',
            healthyThresholdCount: 1
        });
    }
}
