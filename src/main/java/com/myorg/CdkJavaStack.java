package com.myorg;

import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcProps;
import software.amazon.awscdk.services.ec2.Peer;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SecurityGroupProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ClusterProps;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.NetworkLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.NetworkLoadBalancedFargateServiceProps;
import software.amazon.awscdk.services.ecs.patterns.NetworkLoadBalancedTaskImageOptions;
// import software.amazon.awscdk.services.sqs.Queue;
// import software.amazon.awscdk.core.Duration;

public class CdkJavaStack extends Stack {
    public CdkJavaStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CdkJavaStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

       // Create VPC with a AZ limit of two.
       Vpc vpc = new Vpc(this, "MyJavaAppVpc", VpcProps.builder().maxAzs(2).build());

       // Enable HTTP and HTTPS ports for webserver
       SecurityGroup webserverSG = new SecurityGroup(this, "MyJavaAppWebSG", SecurityGroupProps.builder().allowAllOutbound(true).vpc(vpc).build());   
       
       // HTTP Rule    
       webserverSG.addIngressRule(Peer.anyIpv4(), Port.tcp(80));
       // HTTPS Rule
       webserverSG.addIngressRule(Peer.anyIpv4(), Port.tcp(443));

       // Create the ECS Service
       Cluster cluster = new Cluster(this, "MyJavaAppEc2Cluster", ClusterProps.builder().vpc(vpc).build());

       // Use the ECS Network Load Balanced Fargate Service construct to create a ECS service
       NetworkLoadBalancedFargateService fargateService = new NetworkLoadBalancedFargateService(
               this,
               "MyJavaAppFargateService",
               NetworkLoadBalancedFargateServiceProps.builder()
                       .cluster(cluster)
                       .taskImageOptions(NetworkLoadBalancedTaskImageOptions.builder()
                               .image(ContainerImage.fromAsset("application"))
                               .build())
                       .build());
    }
}
