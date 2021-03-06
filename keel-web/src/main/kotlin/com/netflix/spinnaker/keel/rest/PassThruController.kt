package com.netflix.spinnaker.keel.rest

import com.netflix.spinnaker.keel.clouddriver.CloudDriverService
import com.netflix.spinnaker.keel.clouddriver.model.ActiveServerGroup
import com.netflix.spinnaker.keel.clouddriver.model.toActive
import com.netflix.spinnaker.keel.core.api.DEFAULT_SERVICE_ACCOUNT
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This controller passes through calls to downstream services (e.g., clouddriver).
 *
 * It's used only for testing.
 */
@RestController
@ConditionalOnProperty("tests.passthru", matchIfMissing = false)
@RequestMapping(path = ["/test"])
class PassThruController(
  private val cloudDriverService: CloudDriverService
) {

  @GetMapping(
    path = ["/applications/{app}/clusters/{account}/{cluster}/{cloudProvider}/{region}/serverGroups"]
  )
  fun getActiveServerGroups(
    @PathVariable("app") app: String,
    @PathVariable("account") account: String,
    @PathVariable("cluster") cluster: String,
    @PathVariable("cloudProvider") cloudProvider: String,
    @PathVariable("region") region: String
  ): List<ActiveServerGroup> =
    runBlocking {
      cloudDriverService
        .listServerGroups(DEFAULT_SERVICE_ACCOUNT, app, account, cluster, cloudProvider, region)
        .let { c ->
          c.serverGroups
            .filterNot { it.disabled }
            .map { it.toActive(c.accountName) }
        }
    }
}
