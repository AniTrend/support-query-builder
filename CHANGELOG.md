> This project adheres to [Semantic Versioning](http://semver.org/).

Change Log
==========

Version 0.1.3-alpha01 *(2021-03-09)*
----------------------------

- **Breaking**: Renamed `matches`, `notEquals` and `contains*` extsion functions [df4f3f1](https://github.com/AniTrend/support-query-builder/commit/df4f3f128944ebadc4ea3207062c6105ac28961d) [7baa9a2](https://github.com/AniTrend/support-query-builder/commit/7baa9a28774f4482183a2a5acca52ecdd21069f1) [ead9614](https://github.com/AniTrend/support-query-builder/commit/ead961429c92cb8bf2d7073212c8e62495056070)

- **New**: `Join` and `From` extensions for inline enclosures dsl style [5f34a9c](https://github.com/AniTrend/support-query-builder/commit/5f34a9c231890cc5a33c6f809ce18493a0040832)

- **New**: Criteria extsion functions on `String` type [c5119a9](https://github.com/AniTrend/support-query-builder/commit/c5119a975bcc2450ccd4782e3f45e1c8af711c52)

- **Fix**: Correct parameter building producing params with array objects [adbc6ce](https://github.com/AniTrend/support-query-builder/commit/adbc6ce7d234b15666ff2984ca4edc9c948f3023)

- **Fix**: Multiple implicit chain joins on builder [4d08af4](https://github.com/AniTrend/support-query-builder/commit/4d08af4dee16f0ae95623d15aec39b2afd93bb5e)

Version 0.1.2-alpha01 *(2021-03-08)*
----------------------------

- **Fix**: Replace `implementation` with `api` in annotation proccessor [16648da](https://github.com/AniTrend/support-query-builder/commit/16648da9f641e37717a1e81e22ca30f7c71062bf)

Version 0.1.1-alpha01 *(2021-03-08)*
----------------------------

- **Fix**: Allow `order by` to be applied on any type of `Projection` and not just a `Projection.Column` [2f5e6a9](https://github.com/AniTrend/support-query-builder/commit/2f5e6a9814f50545a555da6d5fbab5dd18101f97)

Version 0.1.0-alpha01 *(2021-03-07)*
----------------------------

- **New**: Initial project release with room annotation proccessor [2f5e6a9](https://github.com/AniTrend/support-query-builder/commit/2f5e6a9814f50545a555da6d5fbab5dd18101f97)
