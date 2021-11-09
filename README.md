# Sample Java Spring Boot based API Application on AWS Fargate using AWS CDK

This is a sample AWS CDK (JAVA) project, which deployes a sample spring boot app to AWS fargate.

It builds the java application directly from the assets provided in the application directory.
Docker file is used to build the application.

It creates all the associated networking constructs, enables port 80 and 443 for web access.

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!

## Issues

* Deployment infinity loop. (ECS fails and retries)
