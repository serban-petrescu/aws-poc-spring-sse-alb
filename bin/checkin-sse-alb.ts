#!/usr/bin/env node
import "source-map-support/register";
import * as cdk from "@aws-cdk/core";
import { CheckinSseAlbStack } from "../lib/checkin-sse-alb-stack";

const app = new cdk.App();
new CheckinSseAlbStack(app, "CheckinSseAlbStack", {
    env: {
        region: "eu-central-1",
    },
});
