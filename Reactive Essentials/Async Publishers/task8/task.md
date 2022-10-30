Generate `Publisher` which returns a given `value` only in case the `value` is 
in bounds `[min, max]`.
In case the `value` is out of bounds return an error-ing `Publisher` instance.
   
<div class="hint">
  Use <code>Flux.just</code> and <code>Flux.error</code>.
  Consider simple <code>if-else</code> instead of writing complex reactive pipe-line
</div>